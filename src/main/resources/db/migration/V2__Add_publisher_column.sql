-- catalog-service/src/main/resources/db/migration/.
-- ./V2__Add_publisher_column.sql

ALTER TABLE book
ADD COLUMN publisher varchar(255);
