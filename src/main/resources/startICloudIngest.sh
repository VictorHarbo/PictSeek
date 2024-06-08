CONF_PATH=/home/admin/conf/PictSeek-devel.yaml

# Extract the username
username=$(yq '.icloudDownloader.username' $CONF_PATH)
# Extract the password
password=$(yq '.icloudDownloader.password' $CONF_PATH)
downloadDir=$(yq '.icloudDownloader.downloadDirectory' $CONF_PATH)




docker run -i -v "$downloadDir":/downloadedImages icloudpd/icloudpd:latest icloudpd --directory /downloadedImages --username "$username" --password $password --recent 10 --skip-videos --folder-structure none
