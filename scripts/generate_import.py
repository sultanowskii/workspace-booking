from json import dumps

def main():
    room = dict(
        officeId=1,
        name='large room',
        walls=[
            dict(
                x1=0,
                y1=0,
                x2=4001,
                y2=0,
            ),
            dict(
                x1=4001,
                y1=0,
                x2=4001,
                y2=4001,
            ),
            dict(
                x1=4001,
                y1=4001,
                x2=0,
                y2=4001,
            ),
            dict(
                x1=0,
                y1=4001,
                x2=0,
                y2=0,
            )
        ],
        workplaces=[],
        meetingRooms=[],
    )

    for y in range(1, 8, 2):
        for x in range(1, 4001, 2):
            room['workplaces'].append(
                dict(
                    numberOfMonitors=1,
                    x=x,
                    y=y,
                    width=1,
                    height=1,
                )
            )
    
    for y in range(9, 20, 5):
        for x in range(1, 4001, 5):
            room['meetingRooms'].append(
                dict(
                    name=f'meeting room {x * y}',
                    x=x,
                    y=y,
                    width=4,
                    height=4,
                )
            )

    s = dumps(room, indent=4)
    with open('import/gen.json', 'w') as f:
        f.write(s)


if __name__ == '__main__':
    main()
