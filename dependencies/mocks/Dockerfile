FROM node:6.11-slim

#Sele
WORKDIR /mocks

#Copy mock files
ADD payment /mocks/payment

#Copy mock sh file
ADD startMocks.sh /mocks/startMocks.sh
RUN chmod +x /mocks/startMocks.sh

#Expose ports
EXPOSE 8083

#Install stubby4node
RUN npm install -g stubby

#RUN Mocks
ENTRYPOINT ["./startMocks.sh"]
