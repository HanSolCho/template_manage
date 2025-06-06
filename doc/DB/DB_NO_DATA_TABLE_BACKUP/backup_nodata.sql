-- H2 2.3.232; 
;              
CREATE USER IF NOT EXISTS "SA" SALT 'a9662e9f61745565' HASH '158fbc7345260a45522e47ea27f20c094349872dcd899652a33dd4b0bc1b37a0' ADMIN;          
CREATE CACHED TABLE "PUBLIC"."CONTENT"(
    "ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL,
    "DATE" TIMESTAMP(6) NOT NULL,
    "NAME" CHARACTER VARYING(255) NOT NULL,
    "PROVIDER" CHARACTER VARYING(255) NOT NULL,
    "TEMPLATE_ID" BIGINT NOT NULL
);            
ALTER TABLE "PUBLIC"."CONTENT" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_6" PRIMARY KEY("ID");       
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.CONTENT;  
CREATE CACHED TABLE "PUBLIC"."CONTENT_ITEM_DATA"(
    "ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL,
    "ITEM_VALUE" CHARACTER VARYING(255) NOT NULL,
    "CONTENT_ID" BIGINT NOT NULL,
    "ITEM_ID" BIGINT NOT NULL
);
ALTER TABLE "PUBLIC"."CONTENT_ITEM_DATA" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_8" PRIMARY KEY("ID");             
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.CONTENT_ITEM_DATA;        
CREATE CACHED TABLE "PUBLIC"."ITEM"(
    "ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL,
    "DATE" TIMESTAMP(6) NOT NULL,
    "IS_DELETED" BOOLEAN NOT NULL,
    "NAME" CHARACTER VARYING(255) NOT NULL,
    "PROVIDER" CHARACTER VARYING(255) NOT NULL,
    "TYPE" TINYINT NOT NULL
);  
ALTER TABLE "PUBLIC"."ITEM" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_22" PRIMARY KEY("ID");         
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.ITEM;     
CREATE CACHED TABLE "PUBLIC"."ITEM_OPTION"(
    "ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL,
    "IS_DELETED" BOOLEAN NOT NULL,
    "OPTION_VALUE" CHARACTER VARYING(255) NOT NULL,
    "ITEM_ID" BIGINT NOT NULL
);   
ALTER TABLE "PUBLIC"."ITEM_OPTION" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_F" PRIMARY KEY("ID");   
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.ITEM_OPTION;              
CREATE CACHED TABLE "PUBLIC"."ONJ_USER"(
    "NUM" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL,
    "ID" CHARACTER VARYING(255) NOT NULL,
    "PASSWORD" CHARACTER VARYING(255) NOT NULL,
    "ROLE" CHARACTER VARYING(255) NOT NULL
);     
ALTER TABLE "PUBLIC"."ONJ_USER" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_B" PRIMARY KEY("NUM");     
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.ONJ_USER; 
CREATE CACHED TABLE "PUBLIC"."TEMPLATE"(
    "ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL,
    "ACCESS_LEVEL" ENUM('PRIVATE', 'PUBLIC') NOT NULL,
    "DATE" TIMESTAMP(6) NOT NULL,
    "IS_DELETED" BOOLEAN NOT NULL,
    "NAME" CHARACTER VARYING(255) NOT NULL,
    "PROVIDER" CHARACTER VARYING(255) NOT NULL,
    "TYPE" CHARACTER VARYING(255) NOT NULL
);        
ALTER TABLE "PUBLIC"."TEMPLATE" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_D" PRIMARY KEY("ID");      
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.TEMPLATE; 
CREATE CACHED TABLE "PUBLIC"."TEMPLATE_ITEM"(
    "TEMPLATE_ID" BIGINT NOT NULL,
    "ITEM_ID" BIGINT NOT NULL
);              
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.TEMPLATE_ITEM;            
ALTER TABLE "PUBLIC"."ITEM" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_2" CHECK("TYPE" BETWEEN 0 AND 2) NOCHECK;      
ALTER TABLE "PUBLIC"."CONTENT" ADD CONSTRAINT "PUBLIC"."FKMW716JXAMPUTLY1PGGLWAK9Q6" FOREIGN KEY("TEMPLATE_ID") REFERENCES "PUBLIC"."TEMPLATE"("ID") NOCHECK;  
ALTER TABLE "PUBLIC"."TEMPLATE_ITEM" ADD CONSTRAINT "PUBLIC"."FKHUWPB04XIR9P6GUGJEB9TY3SP" FOREIGN KEY("ITEM_ID") REFERENCES "PUBLIC"."ITEM"("ID") NOCHECK;    
ALTER TABLE "PUBLIC"."TEMPLATE_ITEM" ADD CONSTRAINT "PUBLIC"."FK8VK17NWT5T804LSCMF97EVG8O" FOREIGN KEY("TEMPLATE_ID") REFERENCES "PUBLIC"."TEMPLATE"("ID") NOCHECK;            
ALTER TABLE "PUBLIC"."ITEM_OPTION" ADD CONSTRAINT "PUBLIC"."FKPIUFBIJQ6M8L18JF9A0NWNY62" FOREIGN KEY("ITEM_ID") REFERENCES "PUBLIC"."ITEM"("ID") NOCHECK;      
ALTER TABLE "PUBLIC"."CONTENT_ITEM_DATA" ADD CONSTRAINT "PUBLIC"."FK98Q6EIPTGXX9YUE3X6JBN7WL7" FOREIGN KEY("ITEM_ID") REFERENCES "PUBLIC"."ITEM"("ID") NOCHECK;
ALTER TABLE "PUBLIC"."CONTENT_ITEM_DATA" ADD CONSTRAINT "PUBLIC"."FKGH9OX9GMGDH7QT75D1U2K6WYX" FOREIGN KEY("CONTENT_ID") REFERENCES "PUBLIC"."CONTENT"("ID") NOCHECK;          
