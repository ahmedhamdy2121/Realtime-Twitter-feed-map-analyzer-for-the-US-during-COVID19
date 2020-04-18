#!/bin/sh
# Lightning Visualization Server installation

echo "Starting setting up Lightning"

echo "Updating system outdated libraries"
yum update -y nss curl libcurl git
echo "System outdated libraries (nss curl libcurl git) have been updated"

echo "Downloading nvm"
curl -o- https://raw.githubusercontent.com/creationix/nvm/v0.30.1/install.sh | bash

echo "Setting environment variables for NodeJS"
source ~/.bash_profile
export NVM_NODEJS_ORG_MIRROR=http://nodejs.org/dist
echo "Environment variables set for NodeJS"

echo "Installing NodeJS v6.3.0"
nvm install v6.3.0
echo "NodeJS v6.3.0 installed"

echo "Updating npm"
npm i -g npm@latest
echo "npm updated"

echo "Installing Lightning Server"
npm install -g lightning-server
echo "Lightning Server installed"

echo "Lightning Visualization Server has been installed successfully"
echo "You can open the server from local host port 3000"