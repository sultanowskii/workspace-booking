from typing import Any, Callable
from faker import Faker
from faker.providers import automotive
from random import uniform as random_float, random

faker = Faker(locale='en_US')
faker.add_provider(automotive)
unique_faker: Faker = faker.unique

Generator = Callable[[], dict[str, Any]]


def office():
    name = unique_faker.catch_phrase() + str(faker.random_int(1, 1000))
    address = faker.address()
    return dict(
        name=name,
        address=address,
    )


def room(office_id: int) -> Generator:
    def _room():
        name = faker.license_plate()
        return dict(
            office_id=office_id,
            name=name,
        )
    return _room


def room_walls(room_id: int) -> list[dict[str, Any]]:
    n = random_float(1000, 10000)
    return [
        dict(
            room_id=room_id,
            x1=0,
            y1=0,
            x2=n,
            y2=0,
        ),
        dict(
            room_id=room_id,
            x1=n,
            y1=0,
            x2=n,
            y2=n,
        ),
        dict(
            room_id=room_id,
            x1=n,
            y1=n,
            x2=0,
            y2=n,
        ),
        dict(
            room_id=room_id,
            x1=0,
            y1=n,
            x2=0,
            y2=0,
        ),
    ]


def meeting_room(room_id: int) -> Generator:
    def _meeting_room():
        name = faker.color_name() + ' ' + faker.country()
        return dict(
            room_id=room_id,
            name=name,
        )
    return _meeting_room


def meeting_rooms_visuals(meeting_room_count: int, meeting_room_start_id: int, room_length: float) -> list[dict]:
    if meeting_room_count == 0:
        return []

    delta = room_length / meeting_room_count
    size = delta / 5
    objs = [
        dict(
            meeting_room_id=meeting_room_start_id+i,
            x=delta*i,
            y=0,
            width=size,
            height=size,
        )
        for i in range(meeting_room_count)
    ]
    return objs


def workplaces_visuals(workplace_count: int, workplace_start_id: int, room_length: float) -> list[dict]:
    if workplace_count == 0:
        return []

    delta = room_length / workplace_count
    size = delta / 5
    objs = [
        dict(
            workplace_id=workplace_start_id+i,
            x=delta*i,
            y=room_length-delta,
            width=size,
            height=size,
        )
        for i in range(workplace_count)
    ]
    return objs


def workplace(room_id: int) -> Generator:
    def _workplace():
        return dict(
            room_id=room_id,
            number_of_monitors=faker.random_int(0, 6),
        )
    return _workplace


def employee_group() -> dict[str, Any]:
    return dict(
        name=faker.color_name() + ' ' + faker.city() + str(faker.random_int(1, 100))
    )


def user() -> dict[str, Any]:
    return dict(
        username=unique_faker.user_name(),
        password=faker.password(16),
        role='EMPLOYEE',
    )


def employee(user_id: int, employee_group_id: int) -> dict[str, Any]:
    return dict(
        user_id=user_id,
        employee_group_id=employee_group_id,
        full_name=faker.name(),
    )


def times(generate: Generator, _n: int) -> list[dict[str, Any]]:
    return [generate() for _ in range(_n)]


def flatten(l: list[list[Any]]) -> list[Any]:
    return [sub_el for el in l for sub_el in el]


def sanitize(s: str) -> str:
    return s.replace("'", '`')


def ser(v) -> str:
    s = sanitize(str(v))
    if isinstance(v, int):
        return s
    elif isinstance(v, str):
        return f"'{s}'"
    return s


def generate_insert(table: str, fields: list[str], objects: list[dict[str, Any]]) -> str:
    if len(objects) == 0:
        return ''

    s = f'INSERT INTO {table} ({", ".join(fields)}) VALUES\n'

    for i, obj in enumerate(objects):
        if i == len(objects) - 1:
            end = ';'
        else:
            end = ','
        
        s += f'({", ".join(ser(obj[field]) for field in fields)}){end}\n'
    
    s += '\n'

    return s


def main():
    f = open('scripts/gen.sql', 'w')

    offices = times(office, 1000)
    f.write(generate_insert('office', ['name', 'address'], offices) + '\n')

    rooms = flatten(
        [
            times(room(i + 1), 10)
            for i in range(len(offices))
        ],
    )
    f.write(generate_insert('room', ['office_id', 'name'], rooms) + '\n')

    _room_walls = [room_walls(i + 1) for i in range(len(rooms))]
    f.write(generate_insert('room_wall', ['room_id', 'x1', 'y1', 'x2', 'y2'], flatten(_room_walls)) + '\n')

    meeting_rooms = []
    meeting_room_id = 1
    _meeting_rooms_visuals = []
    for i in range(len(rooms)):
        mrs = times(meeting_room(i + 1), faker.random_int(0, 2))
        meeting_rooms.append(mrs)
        mrvs = meeting_rooms_visuals(len(mrs), meeting_room_id, _room_walls[i][0]['x2'])
        _meeting_rooms_visuals.append(mrvs)
        meeting_room_id += len(mrs)

    f.write(generate_insert('meeting_room', ['room_id', 'name'], flatten(meeting_rooms)) + '\n')
    f.write(generate_insert('meeting_room_visual', ['meeting_room_id', 'x', 'y', 'width', 'height'], flatten(_meeting_rooms_visuals)) + '\n')

    workplaces = []
    workplace_id = 1
    _workplaces_visuals = []
    for i in range(len(rooms)):
        ws = times(workplace(i + 1), 200)
        workplaces.append(ws)
        wvs = workplaces_visuals(len(ws), workplace_id, _room_walls[i][0]['x2'])
        _workplaces_visuals.append(wvs)
        workplace_id += len(ws)

    f.write(generate_insert('workplace', ['room_id', 'number_of_monitors'], flatten(workplaces)) + '\n')
    f.write(generate_insert('workplace_visual', ['workplace_id', 'x', 'y', 'width', 'height'], flatten(_workplaces_visuals)) + '\n')

    employee_groups = times(employee_group, 1000)
    f.write(generate_insert('employee_group', ['name'], employee_groups) +' \n')

    office_employee_groups = []
    for eg_id in range(1, len(employee_groups) + 1):
        for of_id in range(1, len(offices) + 1):
            if random() < 0.05:
                office_employee_groups.append(
                    dict(
                        office_id=of_id,
                        employee_group_id=eg_id,
                    ),
                )
    f.write(generate_insert('office_employee_group', ['office_id', 'employee_group_id'], office_employee_groups) + '\n')

    users = times(user, 40000)
    f.write(generate_insert('users', ['username', 'password', 'role'], users) + '\n')

    employees = [employee(user_id, faker.random_int(1, len(employee_groups))) for user_id in range(1, len(users) + 1)]
    f.write(generate_insert('employee', ['user_id', 'employee_group_id', 'full_name'], employees) + '\n')

    workplace_bookings = [
        dict(
            employee_id=w_id % len(employees) + 1,
            workplace_id=w_id,
            booking_date=f'2024-11-{day:02}'
        )
        for w_id in range(1, min(len(workplaces), len(employees)))
        for day in range(1, 31)
    ]
    f.write(generate_insert('workplace_booking', ['employee_id', 'workplace_id', 'booking_date'], workplace_bookings) + '\n')

    meeting_room_bookings = []
    meeting_participants = []
    for mr_id in range(1, meeting_room_id):
        mrb_id = 1
        for day in range(1, 31):
            for hour in range(9, 17):
                meeting_room_bookings.append(
                    dict(
                        employee_id=mr_id,
                        meeting_room_id=mr_id,
                        booking_date=f'2024-11-{day:02}',
                        start_time=f'{hour:02}:00:00',
                        end_time=f'{hour+1:02}:00:00',
                        description=faker.text(),
                    ),
                )
                meeting_participants.append(
                    [
                        dict(
                            meeting_room_booking_id=mrb_id,
                            employee_id=faker.random_int(1, len(employees)),
                        )
                        for _ in range(6)
                    ]
                )
                mrb_id += 1

    for i in range(0, len(meeting_room_bookings), 100_000):
        f.write(generate_insert('meeting_room_booking', ['employee_id', 'meeting_room_id', 'booking_date', 'start_time', 'end_time', 'description'], meeting_room_bookings[i:i+100_000]) + '\n')

    flattened_meeting_participants = flatten(meeting_participants)
    for i in range(0, len(flattened_meeting_participants), 100_000):
        f.write(generate_insert('meeting_participant', ['meeting_room_booking_id', 'employee_id'], flattened_meeting_participants[i:i+100_000]) + '\n')

    f.close()


if __name__ == '__main__':
    main()
