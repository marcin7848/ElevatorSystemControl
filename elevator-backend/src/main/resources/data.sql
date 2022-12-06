INSERT INTO elevators (current_floor, current_status) VALUES (0, 0);
INSERT INTO elevators (current_floor, current_status) VALUES (5, 0);
INSERT INTO elevators (current_floor, current_status) VALUES (4, 0);
INSERT INTO elevators (current_floor, current_status) VALUES (-2, 0);

INSERT INTO elevator_floor (floor, direction, position, id_elevator) VALUES (6, 0, 0, 1);
INSERT INTO elevator_floor (floor, direction, position, id_elevator) VALUES (3, 1, 1, 1);
INSERT INTO elevator_floor (floor, direction, position, id_elevator) VALUES (-2, 0, 2, 1);
INSERT INTO elevator_floor (floor, direction, position, id_elevator) VALUES (2, -1, 3, 1);

INSERT INTO elevator_floor (floor, direction, position, id_elevator) VALUES (2, 0, 0, 2);
INSERT INTO elevator_floor (floor, direction, position, id_elevator) VALUES (3, -1, 1, 2);
INSERT INTO elevator_floor (floor, direction, position, id_elevator) VALUES (4, 1, 2, 2);
INSERT INTO elevator_floor (floor, direction, position, id_elevator) VALUES (5, -1, 3, 2);

INSERT INTO elevator_floor (floor, direction, position, id_elevator) VALUES (-2, 0, 0, 3);
INSERT INTO elevator_floor (floor, direction, position, id_elevator) VALUES (2, -1, 1, 3);
INSERT INTO elevator_floor (floor, direction, position, id_elevator) VALUES (1, 1, 2, 3);
INSERT INTO elevator_floor (floor, direction, position, id_elevator) VALUES (5, 0, 3, 3);