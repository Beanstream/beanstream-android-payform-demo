<img src="http://www.beanstream.com/wp-content/uploads/2015/08/Beanstream-logo.png" />
# PayForm Demo for Android

This demonstrates how to setup and use PayForm from a basic app.

## PayForm

PayForm is a small client-side Android library that handles customer credit card input within the merchant's app. Most apps will let users launch PayForm from something like a button action.

This Android library limits the scope of a merchant's PCI compliance by removing the need for them to pass the sensitive information (credit card number, CVD, or expiry) through their servers and from having to write and store code that comes in contact with that sensitive information.

## PayForm Library
This demo uses the [PayForm](https://github.com/Beanstream/beanstream-android-payform) library

## Setup Artifactory Credentials
Add your Artifactory credentials to ***[USER_HOME]/.gradle/gradle.properties***
```
# Artifactory Credentials
bic_artifactory_url=https://beanstream.jfrog.io/beanstream

## Replace USERNAME and PASSWORD
bic_artifactory_user=USERNAME
bic_artifactory_password=PASSWORD
```