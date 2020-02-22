# Set the base image
FROM alpine:3.6

RUN apk -v --update add \
        python \
        py-pip \
        groff \
        less \
        mailcap \
        mysql-client \
        curl \
        && \
    pip install --upgrade awscli s3cmd python-magic && \
    apk -v --purge del py-pip && \
    rm /var/cache/apk/*

RUN apk add --update \
 python \
 curl \
 which \
 bash

RUN apk add --update --no-cache mysql-client

RUN curl -sSL https://sdk.cloud.google.com | bash

ENV PATH $PATH:/root/google-cloud-sdk/bin

# Set Default Environment Variables
#ENV TARGET_DATABASE_PORT=3306
#ENV SLACK_ENABLED=false
#ENV SLACK_USERNAME=kubernetes-s3-mysql-backup

#COPY gcp-service-account-key.json /
#RUN chmod +x /gcp-service-account-key.json

 
# Copy backup script and execute
COPY mysql-backup.sh /
RUN chmod +x /mysql-backup.sh
CMD ["sh", "/mysql-backup.sh"]


 
