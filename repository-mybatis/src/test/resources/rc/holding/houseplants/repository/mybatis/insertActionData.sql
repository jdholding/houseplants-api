insert into  houseplants.actions (
    id,
    plant_id,
    user_id,
    type_id,
    date_created)
values (
    431,
    134,
    21,
    3,
    timestamp with time zone '2023-04-23 12:29:17.498Z');

insert into  houseplants.actions (
    id,
    plant_id,
    user_id,
    type_id,
    date_created)
values (
    666,
    136,
    21,
    1,
    timestamp with time zone '2022-05-13 12:34:17.498Z');

insert into  houseplants.actions (
    id,
    plant_id,
    user_id,
    type_id,
    date_created)
values (
    667,
    136,
    21,
    3,
    timestamp with time zone '2022-10-13 12:34:17.498Z');

insert into  houseplants.actions (
    id,
    plant_id,
    user_id,
    type_id,
    date_created)
values (
    777,
    631,
    21,
    2,
    timestamp with time zone '2023-03-13 12:34:17.498Z');

insert into  houseplants.actions (
    id,
    plant_id,
    user_id,
    type_id,
    date_created)
values (
    999,
    124,
    23,
    1,
    timestamp with time zone '2023-03-10 12:34:17.498Z');

insert into houseplants.action_types (
    id,
    label)
values (
    1,
    'Fertilize');

insert into houseplants.action_types (
    id,
    label)
values (
    2,
    'Re-pot');

insert into houseplants.action_types (
    id,
    label)
values (
    3,
    'Prune');