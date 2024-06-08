CONF_PATH=/home/admin/conf/PictSeek-devel.yaml

# Extract the username
username=$(yq -r '.icloudDownloader.user' $CONF_PATH)
# Extract the password
password=$(yq -r '.icloudDownloader.password' $CONF_PATH)
downloadDir=$(yq  -r '.icloudDownloader.downloadDirectory' $CONF_PATH)


echo "Starting download for user: " $username

docker run -i -v "$downloadDir:/downloadedImages" icloudpd/icloudpd:latest icloudpd --directory /downloadedImages --username $username --password $password --recent 10 --skip-videos --folder-structure none
