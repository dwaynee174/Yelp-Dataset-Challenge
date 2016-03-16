
/* creating user_yelp table*/
create table user_yelp(
yelping_since varchar2(10),
review_count integer,
user_name varchar2(15), 
user_id varchar2(20) primary key,
fans integer,
average_stars decimal);

/* create friends table */
create table friends(
user_id varchar2(32),
friend_id varchar2(32));

/* create elite table */
Create table elite(
user_id varchar2(32),
year_elite integer);


/* creating votes table */
create table votes(
user_id varchar2(30),
funny integer,
useful integer,
cool integer);

/* create checkin table */
create table checkin(
business_id varchar2(32),
type_check varchar2(20),
checkin_time integer,
checkin_day integer,
checkin integer);

/* create compliment table */
create table compliment(
user_id varchar2(32),
key_type varchar2(16),
key_value integer);

/* create business table */
CREATE TABLE business (
business_id VARCHAR(255),
full_address VARCHAR(255),
city VARCHAR(255),
review_count NUMBER,
business_name VARCHAR(255),
longitude varchar2(255),
latitude varchar2(255),
state VARCHAR(255),
stars NUMBER );

/* create business_attribute table */
create table business_attribute(
bid varchar2(32),
attribute_key varchar2(225),
attribute_value varchar2(255));

/* create business_category table */
create table business_category(
business_id varchar2(32),
b_category varchar2(225),
b_category_sub varchar2(225));


/* creating operatingHours table */
create table operatingHours(
bid varchar2(32),
days varchar2(10), 
openhour varchar2(5),
closehour varchar2(5));


/* create reviews table */


CREATE TABLE Reviews (
user_id VARCHAR(255),
review_id VARCHAR(255),
stars NUMBER,
rev_date varchar(255),
text varchar(4000),
business_id varchar(255)
);

/* create category table */
create table categories (
categ varchar(255)
);

/* insert statements for categories table */

insert into categories values ('Active Life');
insert into categories values ('Arts and Entertainment');
insert into categories values ('Automotive');
insert into categories values ('Car Rental');
insert into categories values ('Cafes');
insert into categories values ('Beauty and Spas');
insert into categories values ('Convinience Stores');
insert into categories values ('Dentists');
insert into categories values ('Doctors');
insert into categories values ('Drugstores');
insert into categories values ('Department Stores');
insert into categories values ('Education');
insert into categories values ('Event Planning and Services');
insert into categories values ('Flowers and Gifts');
insert into categories values ('Food');
insert into categories values ('Health and Medical');
insert into categories values ('Home Services');
insert into categories values ('Home and Garden');
insert into categories values ('Hospitals');
insert into categories values ('Hotels and Travel');
insert into categories values ('Hardware Stores');
insert into categories values ('Grocery');
insert into categories values ('Medical Centers');
insert into categories values ('Nurseries and Gardening');
insert into categories values ('Nightlife');
insert into categories values ('Restaurants');
insert into categories values ('Shopping');
insert into categories values ('Transportation');

/* create subcategory table */
create table subcategory(busid varchar(255),
cat varchar2(255),
subcategory varchar2(255));



