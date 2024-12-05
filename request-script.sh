#!/bin/bash

TIMES=10
for i in $(eval echo "{1..$TIMES}")
do
    curl --request POST --url http://localhost:80/api/iban/validate \
    --header "Content-Type: application/json" \
    --data '{"iban": "DE89370400440532013000"}'
    
    curl --request POST --url http://localhost:80/api/iban/validate \
    --header "Content-Type: application/json" \
    --data '{"iban": "GB82WEST12345698765432"}'
    
    curl --request POST --url http://localhost:80/api/iban/validate \
    --header "Content-Type: application/json" \
    --data '{"iban": "FR7630006000011234567890189"}'
    
    curl --request POST --url http://localhost:80/api/iban/validate \
    --header "Content-Type: application/json" \
    --data '{"iban": "NL91ABNA0417164300"}'
    
    curl --request POST --url http://localhost:80/api/iban/validate \
    --header "Content-Type: application/json" \
    --data '{"iban": "ES9121000418450200051332"}'
    
    curl --request POST --url http://localhost:80/api/iban/validate \
    --header "Content-Type: application/json" \
    --data '{"iban": "IT60X0542811101000000123456"}'
    
    curl --request POST --url http://localhost:80/api/iban/validate \
    --header "Content-Type: application/json" \
    --data '{"iban": "BE68539007547034"}'
    
    curl --request POST --url http://localhost:80/api/iban/validate \
    --header "Content-Type: application/json" \
    --data '{"iban": "CH9300762011623852957"}'
    
    curl --request POST --url http://localhost:80/api/iban/validate \
    --header "Content-Type: application/json" \
    --data '{"iban": "SE4550000000058398257466"}'
    
    curl --request POST --url http://localhost:80/api/iban/validate \
    --header "Content-Type: application/json" \
    --data '{"iban": "DK5000400440116243"}'
    
    sleep 5
done