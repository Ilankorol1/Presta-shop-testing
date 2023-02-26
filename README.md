# Assignment 3: Software Quality Engineering
This is a repository for assignment 3 of the Software Quality Engineering course at the [Ben-Gurion University](https://in.bgu.ac.il/), Israel.

## Assignment Description
In this assignment, we tested an open-source software called [PrestaShop](https://github.com/PrestaShop/PrestaShop).

PrestaShop is an Open Source e-commerce web application, committed to providing the best shopping cart experience for both merchants and customers. 
It is written in PHP, is highly customizable, supports all the major payment services, is translated in many languages and localized for many countries,
has a fully responsive design (both front and back office), etc. [See all the available features][available-features].

<p align="center">
  <img src="https://user-images.githubusercontent.com/2137763/201319765-9157f702-4970-4258-8390-1187de2ad587.png" alt="PrestaShop 8.0 back office"/>
</p>

## Installation
The easiest way is to use the official [PrestaShop image](https://hub.docker.com/r/prestashop/prestashop/) on Docker hub.

This image is initialised in 3 steps:

### Step 1: Create a network:
PrestaShop image needs a database container to manage database tables. Hence, we need a docker network to establish communication between PrestaShop and database container. Open terminal and run following command to create a docker network:
```
docker network create myprestashopnetwork
```
Of course, myprestashopnetwork is custom name for network so you can choose whatever name you want for your instance.
### Step 2: Create a database container:
The database container is needed to manage PrestaShop database tables. This container is created using mysql image. Initialise this container with following command:
```
docker run -ti --name mypsmysql --network myprestashopnetwork -e MYSQL_ROOT_PASSWORD=admin -p 3307:3306 -d mysql:latest
```
Here, “mypsmysql” is name for database container and “myprestashopnetwork” is the network name.
### Step 3: Initialise PrestaShop in docker container
At this point, we have our docker network and database container ready. Now initialise PrestaShop container with following command:
```
docker run -ti --name myprestashop --network myprestashopnetwork -p 8888:80 -d prestashop/prestashop:latest
```
Here:
* “myprestashop” is name for our PrestaShop container
* “myprestashopnetwork” is network name.
* Port number for container is 8888.
* “prestashop/prestashop:latest” is image name with latest tag. So this will install the latest available PrestaShop image in dockerhub. To this day, PrestaShop 1.7.8.5 is available as latest image.

After above steps are complete, go to http://localhost:8888/ and the PrestaShop installation page will load.

From here, install the PrestaShop and use following values at the database connection step:

* Host: mypsmysql
* Login: root
* Password: admin

This is how we start PrestaShop in docker container.
But as you can see, we need to install the PrestaShop after creating container at first time.

### Automatic installation when running container
The docker hub image provides many environment options. Using these options, PrestaShop installs automatically when we initialise container with single command. After following Step-1 and Step-2 from above, run following command:
```
docker run -ti --name myprestashop  --network myprestashopnetwork \
-e PS_INSTALL_AUTO=1 \
-e DB_SERVER=mypsmysql \
-e DB_USER=root \
-e DB_PASSWD=admin \
-e DB_NAME=myprestadb \
-e PS_INSTALL_DB=1 \
-e PS_FOLDER_INSTALL=install_renamed \
-e PS_FOLDER_ADMIN=admin_ps \
-p 8888:80 -d prestashop/prestashop:latest
```
As you can see, we have used many options to configure PrestaShop installation while initialising container:
* __PS_INSTALL_AUTO__: Automatic initialisation flag set to true.
* __DB_SERVER, DB_USER, DB_PASSWD and DB_NAME__: Database container details.
* __PS_INSTALL_DB__: Database installation flag set to true.
* __PS_FOLDER_INSTALL__: The name of install folder to rename after installation.
* __PS_FOLDER_ADMIN__: The name of admin folder after installation.

After automatic installation process is complete, you can access the PrestaShop container on http://localhost:8888.

Please note that the installation takes sometime so the URL “http://localhost:8888/” will not work until the installation is complete. Meanwhile, you can check the logs by running following command:
`docker logs myprestashop`



## What we tested

We tested the cart module that shows the items that have been added to the cart before the customer proceeds to checkout.

The cart module supports signed-in checkout and guest checkout. It also supports a Back to shopping link

We chose to test the following user stories: 

*User story:* A customer adds a product to the cart.

*Preconditions:* There is a store with a product.

*Expected outcome:* The product is added to the cart.

*User story:* A seller changes the price of a product.

*Preconditions:* There is a store with a product, and the seller has permission to change the product price.

*Expected outcome:* The product price is changed.

## How we tested
We used two different testing methods:
1. [Cucumber](https://cucumber.io/), a BDD testing framework.
2. [Provengo](https://provengo.tech/), a story-based testing framework.

Each of the testing methods is elaborated in its own directory.

[available-features]: https://www.prestashop.com/en/online-store-builder
