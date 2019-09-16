MessageBird's REST API for Java
=================================
This repository contains the open source Java client for MessageBird's REST API. Documentation can be found at: https://developers.messagebird.com.

[![Build Status](https://travis-ci.org/messagebird/java-rest-api.svg?branch=master)](https://travis-ci.org/messagebird/java-rest-api)
![Maven metadata URI](https://img.shields.io/maven-metadata/v/http/central.maven.org/maven2/com/messagebird/messagebird-api/maven-metadata.xml.svg)


Requirements
------------
- [Sign up](https://dashboard.messagebird.com/en/sign-up) for a free MessageBird account
- Create a new access key in the developers sections
- An application written in Java
- Installed version of maven

Installation
------------
The easiest way to install the MessageBird package is either via github:

```
git clone https://github.com/messagebird/java-rest-api
cd java-rest-api/api
mvn install
```

If you are using maven, please refer to the [mvn repository](https://mvnrepository.com/artifact/com.messagebird/messagebird-api) and choose your desired package version.

Examples
--------
We have put some self-explanatory examples in the [examples](https://github.com/messagebird/java-rest-api/tree/master/examples/src/main/java) directory, but here is a quick example on how to get started. Assuming the installation was successful, you can import the messagebird package like this:

```java
import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.objects.MessageResponse;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.UnauthorizedException;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
```

Then, create an instance of **MessageBirdClient**:

```java
// Create a MessageBirdService
final MessageBirdService messageBirdService = new MessageBirdServiceImpl("test_gshuPaZoeEG6ovbc8M79w0QyM");
// Add the service to the client
final MessageBirdClient messageBirdClient = new MessageBirdClient(messageBirdService);
```

Now you can query the API for information or send a request. For example, if we want to request our balance information you'd do something like this:

```java
    try {
        // Get Balance
        System.out.println("Retrieving your balance:");
        final Balance balance = messageBirdClient.getBalance();

        // Display balance
        System.out.println(balance.toString());

    } catch (UnauthorizedException unauthorized) {
        if (unauthorized.getErrors() != null) {
            System.out.println(unauthorized.getErrors().toString());
        }
        unauthorized.printStackTrace();
    } catch (GeneralException generalException) {
        if (generalException.getErrors() != null) {
            System.out.println(generalException.getErrors().toString());
        }
        generalException.printStackTrace();
    }
```

This will give you something like:
```shell
Retrieving your balance:
Balance{payment='prepaid', type='credits', amount=97}
```

To try out the command line examples follow the above build instructions.
When everything did build successful you can try out the API like this:
```shell
cd examples/target
java -cp examples-1.2.0-jar-with-dependencies.jar ExampleSendMessage test_gshuPaZoeEG6ovbc8M79w0QyM 31612345678 "This is a test message"
```

Please see the other examples for a complete overview of all the available API calls.

Running with unit tests
-----------------------
Running unit tests requires you to create a developer key. You can create one for free at https://dashboard.messagebird.com/en/sign-up,
or when you have one already you can skip registration and go to https://dashboard.messagebird.com/en/settings/developers/access
to create or get your test access key. Once you have such key you can run the unit tests like this:

```shell
mvn test -Ptest -DmessageBirdAccessKey=[your access key] -DmessageBirdMSISDN=[your phone]
```

This will run all unit tests and verifies if everything is running as expected.

Note: If you use by accident use your live key it will send messages and will remove it from your account,
please ensure you use your test key to run your unit tests to avoid loosing your credits.

Proxy support
-------------
If you server doesn't have a direct connection to the internet you can setup a proxy to for the MessageBird service to use.

```java
    final Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.0.0.1", 8080));
    final MessageBirdService messageBirdService = new MessageBirdServiceImpl("test_gshuPaZoeEG6ovbc8M79w0QyM");
    messageBirdService.setProxy(proxy);
```

##### Conversations WhatsApp Sandbox
To use the whatsapp sandbox you need to add `MessageBirdClient.ENABLE_CONVERSATION_API_WHATSAPP_SANDBOX` to the list of features you want enabled. Don't forget to replace `YOUR_ACCESS_KEY` with your actual access key.

```java
    // Create a MessageBirdService
    final MessageBirdService messageBirdService = new MessageBirdServiceImpl("YOUR_ACCESS_KEY");
    // Add the service to the client
    final MessageBirdClient messageBirdClient = new MessageBirdClient(messageBirdService, List.of(MessageBirdClient.Feature.ENABLE_CONVERSATION_API_WHATSAPP_SANDBOX));
```

Documentation
-------------
Complete documentation, instructions, and examples are available at:
[https://developers.messagebird.com](https://developers.messagebird.com).

License
-------
The MessageBird REST Client for Java is licensed under [The BSD 2-Clause License](http://opensource.org/licenses/BSD-2-Clause). Copyright (c) 2015-2019, MessageBird
