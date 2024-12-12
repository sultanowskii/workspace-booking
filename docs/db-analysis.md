# Запросы

## Получение всех рабочих мест в помещении сегодня с отметкой по занятости места

```psql
workspace_booking=# EXPLAIN ANALYZE
SELECT 
    w.*,
    wv.*,
    CASE
        WHEN wb.id IS NOT NULL
        THEN TRUE
        ELSE FALSE
    END AS booked
FROM workplace w
LEFT JOIN workplace_booking wb ON wb.workplace_id = w.id AND wb.booking_date = '2024-11-01'
INNER JOIN workplace_visual wv ON wv.workplace_id = w.id
WHERE w.room_id = 4;
                                                                                        QUERY PLAN                                                                                        
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
 Gather  (cost=1000.85..23589.57 rows=200 width=49) (actual time=0.537..46.420 rows=200 loops=1)
   Workers Planned: 2
   Workers Launched: 2
   ->  Nested Loop  (cost=0.85..22569.57 rows=83 width=49) (actual time=24.878..38.756 rows=67 loops=3)
         ->  Nested Loop Left Join  (cost=0.42..21875.28 rows=83 width=16) (actual time=24.874..38.523 rows=67 loops=3)
               ->  Parallel Seq Scan on workplace w  (cost=0.00..21227.67 rows=83 width=12) (actual time=24.868..38.255 rows=67 loops=3)
                     Filter: (room_id = 4)
                     Rows Removed by Filter: 666600
               ->  Index Scan using workplace_booking_workplace_id_booking_date_key on workplace_booking wb  (cost=0.42..7.80 rows=1 width=8) (actual time=0.003..0.003 rows=1 loops=200)
                     Index Cond: ((workplace_id = w.id) AND (booking_date = '2024-11-01'::date))
         ->  Index Scan using workplace_visual_workplace_id_key on workplace_visual wv  (cost=0.43..8.37 rows=1 width=36) (actual time=0.003..0.003 rows=1 loops=200)
               Index Cond: (workplace_id = w.id)
 Planning Time: 0.624 ms
 Execution Time: 46.480 ms
(14 rows)
```

Как видно, здесь можно ускорить несколько моментов:

- Фильтрация по room_id действует через seq scan (здесь будет полезен b-tree)
- Join с workplace_booking (b-tree на workplace_id)

Индексы:

```psql
CREATE INDEX idx_workplace_room_id ON workplace(room_id);
CREATE INDEX idx_workplace_booking_workplace_id ON workplace_booking(workplace_id);
```

И действительно, заметно значительное ускорение после создания индексов:

```psql
workspace_booking=# EXPLAIN ANALYZE
SELECT
    w.*,
    wv.*,
    CASE
        WHEN wb.id IS NOT NULL
        THEN TRUE
        ELSE FALSE
    END AS booked
FROM workplace w
LEFT JOIN workplace_booking wb ON wb.workplace_id = w.id AND wb.booking_date = '2024-11-01'
INNER JOIN workplace_visual wv ON wv.workplace_id = w.id
WHERE w.room_id = 4;
                                                                              QUERY PLAN                                                                               
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
 Nested Loop  (cost=1.15..3213.43 rows=200 width=49) (actual time=0.055..1.479 rows=200 loops=1)
   ->  Nested Loop Left Join  (cost=0.72..1540.43 rows=200 width=16) (actual time=0.045..0.784 rows=200 loops=1)
         ->  Index Scan using idx_workplace_room_id on workplace w  (cost=0.43..12.93 rows=200 width=12) (actual time=0.028..0.088 rows=200 loops=1)
               Index Cond: (room_id = 4)
         ->  Index Scan using idx_workplace_booking_workplace_id on workplace_booking wb  (cost=0.30..7.64 rows=1 width=8) (actual time=0.003..0.003 rows=1 loops=200)
               Index Cond: (workplace_id = w.id)
               Filter: (booking_date = '2024-11-01'::date)
   ->  Index Scan using workplace_visual_workplace_id_key on workplace_visual wv  (cost=0.43..8.37 rows=1 width=36) (actual time=0.003..0.003 rows=1 loops=200)
         Index Cond: (workplace_id = w.id)
 Planning Time: 1.552 ms
 Execution Time: 1.575 ms
(11 rows)
```

## Получение всех переговорок в помещении сегодня с отметкой по занятости

Здесь ситуация схожа с предыдущим запросом - сущности по своей сути довольно схожи.

```psql
workspace_booking=# EXPLAIN ANALYZE
SELECT
    mr.*,
    mrv.*,
    mrb.*,
    CASE
        WHEN mrb.id IS NOT NULL
        THEN TRUE
        ELSE FALSE
    END AS booked
FROM meeting_room mr
LEFT JOIN meeting_room_booking mrb
    ON mrb.meeting_room_id = mr.id
    AND mrb.booking_date = '2024-11-01'
    AND mrb.start_time <= '13:00:00'
    AND mrb.end_time > '13:00:00'
INNER JOIN meeting_room_visual mrv ON mrv.meeting_room_id = mr.id
WHERE mr.room_id = 4;
                                                                                  QUERY PLAN                                                                                  
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
 Nested Loop  (cost=1201.55..84739.04 rows=5 width=247) (actual time=2.094..88.764 rows=2 loops=1)
   ->  Hash Right Join  (cost=1201.25..84717.33 rows=5 width=210) (actual time=2.063..88.725 rows=2 loops=1)
         Hash Cond: (mrb.meeting_room_id = mr.id)
         ->  Gather  (cost=1000.00..84447.00 rows=26305 width=181) (actual time=0.335..86.339 rows=10018 loops=1)
               Workers Planned: 2
               Workers Launched: 2
               ->  Parallel Seq Scan on meeting_room_booking mrb  (cost=0.00..80816.50 rows=10960 width=181) (actual time=0.079..80.540 rows=3339 loops=3)
                     Filter: ((start_time <= '13:00:00'::time without time zone) AND (end_time > '13:00:00'::time without time zone) AND (booking_date = '2024-11-01'::date))
                     Rows Removed by Filter: 798101
         ->  Hash  (cost=201.23..201.23 rows=2 width=29) (actual time=1.718..1.719 rows=2 loops=1)
               Buckets: 1024  Batches: 1  Memory Usage: 9kB
               ->  Seq Scan on meeting_room mr  (cost=0.00..201.23 rows=2 width=29) (actual time=0.011..1.715 rows=2 loops=1)
                     Filter: (room_id = 4)
                     Rows Removed by Filter: 10016
   ->  Memoize  (cost=0.30..8.31 rows=1 width=36) (actual time=0.015..0.015 rows=1 loops=2)
         Cache Key: mr.id
         Cache Mode: logical
         Hits: 0  Misses: 2  Evictions: 0  Overflows: 0  Memory Usage: 1kB
         ->  Index Scan using meeting_room_visual_meeting_room_id_key on meeting_room_visual mrv  (cost=0.29..8.30 rows=1 width=36) (actual time=0.011..0.011 rows=1 loops=2)
               Index Cond: (meeting_room_id = mr.id)
 Planning Time: 0.539 ms
 Execution Time: 88.823 ms
(22 rows)
```

Добавим индексы (по аналогии с workplace):

```psql
CREATE INDEX idx_meeting_room_room_id ON meeting_room (room_id);
CREATE INDEX idx_meeting_room_booking_meeting_room_id ON meeting_room_booking (meeting_room_id);
```

После добавления индексов также заметно значительное сокращение времени исполнения запроса:

```psql
workspace_booking=# EXPLAIN ANALYZE
SELECT
    mr.*,
    mrv.*,
    mrb.*,
    CASE
        WHEN mrb.id IS NOT NULL
        THEN TRUE
        ELSE FALSE
    END AS booked
FROM meeting_room mr
LEFT JOIN meeting_room_booking mrb
    ON mrb.meeting_room_id = mr.id
    AND mrb.booking_date = '2024-11-01'
    AND mrb.start_time <= '13:00:00'
    AND mrb.end_time > '13:00:00'
INNER JOIN meeting_room_visual mrv ON mrv.meeting_room_id = mr.id
WHERE mr.room_id = 4;
                                                                                  QUERY PLAN                                                                                  
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
 Nested Loop Left Join  (cost=1.00..102.40 rows=5 width=247) (actual time=0.057..0.262 rows=2 loops=1)
   ->  Nested Loop  (cost=0.57..24.93 rows=2 width=65) (actual time=0.042..0.049 rows=2 loops=1)
         ->  Index Scan using idx_meeting_room_room_id on meeting_room mr  (cost=0.29..8.32 rows=2 width=29) (actual time=0.030..0.031 rows=2 loops=1)
               Index Cond: (room_id = 4)
         ->  Index Scan using meeting_room_visual_meeting_room_id_key on meeting_room_visual mrv  (cost=0.29..8.30 rows=1 width=36) (actual time=0.004..0.004 rows=1 loops=2)
               Index Cond: (meeting_room_id = mr.id)
   ->  Index Scan using idx_meeting_room_booking_meeting_room_id on meeting_room_booking mrb  (cost=0.43..38.71 rows=3 width=181) (actual time=0.010..0.103 rows=1 loops=2)
         Index Cond: (meeting_room_id = mr.id)
         Filter: ((start_time <= '13:00:00'::time without time zone) AND (end_time > '13:00:00'::time without time zone) AND (booking_date = '2024-11-01'::date))
         Rows Removed by Filter: 239
 Planning Time: 0.866 ms
 Execution Time: 0.304 ms
(12 rows)
```
