#/bin/sh


############# GCLOUD SET ENV, SERVICE ACCOUNT ####################

gcloud config set project ${GCP_PROJECT_NAME}
gcloud auth activate-service-account --key-file ${SERVICE_ACCOUNT_JSON_KEY_FILE_PATH}${SERVICE_ACCOUNT_JSON_KEY_FILE_NAME}.json
gcloud auth list
gcloud config set account ${SERVICE_ACCOUNT_CLINET_EMAIL}
GOOGLE_APPLICATION_CREDENTIALS=${SERVICE_ACCOUNT_JSON_KEY_FILE_PATH}${SERVICE_ACCOUNT_JSON_KEY_FILE_NAME}.json
################################################################################



################### DB BACKUP STARTED #########################

has_failed=false
now=$(date '+%d-%m-%Y-%H-%M-%S')
LOG=kube-gcp-bucket-mysql-backup-$now.log

echo -e "START:Database backup STARTED at $(date +'%d-%m-%Y %H:%M:%S')." | tee -a /tmp/$LOG

# Set the has_failed variable to false. This will change if any of the subsequent database backups/uploads fail.


# Loop through all the defined databases, seperating by a ,
for CURRENT_DATABASE in ${TARGET_DATABASE_NAMES//,/ }
do
    DUMP=$CURRENT_DATABASE-$now.sql
    # Perform the database backup. Put the output to a variable. If successful upload the backup to S3, if unsuccessful print an entry to the console and the log, and set has_failed to true.
    if sqloutput=$(mysqldump -u $TARGET_DATABASE_USER -h $TARGET_DATABASE_HOST -p$TARGET_DATABASE_PASSWORD -P $TARGET_DATABASE_PORT $CURRENT_DATABASE 2>&1 > /tmp/$DUMP)
    then

        echo -e "SUCCESS:Database backup successfully completed for $CURRENT_DATABASE at $(date +'%d-%m-%Y %H:%M:%S')." | tee -a /tmp/$LOG
		echo -e "SUCCESS:$sqloutput" | tee -a /tmp/$LOG

        # Perform the upload to S3. Put the output to a variable. If successful, print an entry to the console and the log. If unsuccessful, set has_failed to true and print an entry to the console and the log
        if gcpoutput=$(gsutil cp /tmp/$DUMP gs://$GCP_BUCKET_NAME/$GCP_BUCKET_DB_BACKUP_PATH/$DUMP 2>&1)
        then
            echo -e "SUCCESS:Database backup successfully uploaded to bucket $GCP_BUCKET_NAME/$GCP_BUCKET_DB_BACKUP_PATH for $CURRENT_DATABASE at $(date +'%d-%m-%Y %H:%M:%S')." | tee -a /tmp/$LOG
			echo -e "SUCCESS:$gcpoutput" | tee -a /tmp/$LOG
        else
            echo -e "ERROR:Database backup failed to upload for $CURRENT_DATABASE at $(date +'%d-%m-%Y %H:%M:%S')." | tee -a /tmp/$LOG
			echo -e "ERROR:$gcpoutput" | tee -a /tmp/$LOG
            has_failed=true
        fi

    else
        echo -e "ERROR:Database backup FAILED for $CURRENT_DATABASE at $(date +'%d-%m-%Y %H:%M:%S'). Error: $sqloutput" | tee -a /tmp/$LOG
		echo -e "ERROR:$sqloutput" | tee -a /tmp/$LOG
        has_failed=true
    fi

done

echo -e "FINISH:Database backup ENDED at $(date +'%d-%m-%Y %H:%M:%S')." | tee -a /tmp/$LOG

gsutil cp /tmp/$LOG gs://$GCP_BUCKET_NAME/$GCP_BUCKET_DB_BACKUP_LOG/$LOG 2>&1
