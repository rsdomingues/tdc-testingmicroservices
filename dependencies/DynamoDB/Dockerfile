FROM senseyeio/alpine-aws-cli

#Copy aws credentials
COPY aws/ /root/.aws/

#Copy mock sh file
ADD setupDB.sh setupDB.sh
RUN chmod +x setupDB.sh

#Copy json data dynamodb
ADD tables/ tables/
RUN chmod +r -R tables/

ENTRYPOINT ["./setupDB.sh"]