## Setup Okta OAuth2 Authentication Server:
- Sign up at [developer.okta.com](https://developer.okta.com) and choose free Auth0 Platform
- Create a sample web application under **Applications** tab. You can create the application with default settings.
- Create a sample api under **APIs** tab. This is required for **audience** parameter. Provide the **Identifier** value as your websocket api url (example: https://localhost:8080/ws/test).
- After creating the API, authorize the application to access the api from the **APIs** tab in your application dashboard.
- Optionally, you can check the grant types under the **Advance Settings -> Grant Types** tab in your application dashboard. We will be using **Client Credentials** grant type.
- Also, you can check the **Advance Settings -> Endpoints** tab in your application dashboard for all the endpoints require for oauth flow.

## Generate an Oauth2 Access Token using postman:
- Open Postman and create a new request and go to the **Authorization** tab.
- Select **OAuth 2.0** as the auth type, **Client Credentials** as the grant type, and provide the following details:
  - **Access Token URL**: `https://{yourOktaDomain}/oauth2/token`. You can get the same in **OAuth token url** field under **Endpoints** tab.
  - **Client ID**: Your application's client ID. You can get the same in your application dashboard under **Settings** tab.
  - **Client Secret**: Your application's client secret.
  - **Audience**: This is an additional step required by Okta. Under **Advanced->Token Request** section in postman, add a key-value pair where **key=audience** and value is the identifier value of your api created earlier (example: https://localhost:8080/ws/test).
- Click on **Get New Access Token**, you should be able to get token. You can check the sample postman request **Okta Oauth**.
- This token will be used in our websocket connection from postman. For that we need to copy the token and paste it under the headers section of the websocket postman request, since in postman websocket request we do not have option to auto-generate the token in authorization tab.

## Test WebSocket connection using the generated token:
- Start the websocket spring boot application.
- In postman, add the previously generated token in the headers section of the websocket request as **Authorization: Bearer {access_token}** (Check the sample websocket postman collection **Websocket basics**). 
- Click on **Connect** button to initiate the websocket connection. If the token is valid, you should be able to establish the connection successfully otherwise you will get an 401 unauthorized error.
- Kindly note that **issuer-uri** in the application.yml is of the format `https://{yourOktaDomain}/`. For some reason this uri is not present in the **Endpoints** tab in okta application dashboard.