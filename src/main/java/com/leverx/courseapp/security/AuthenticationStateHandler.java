package com.leverx.courseapp.security;

import com.okta.authn.sdk.AuthenticationStateHandlerAdapter;
import com.okta.authn.sdk.resource.AuthenticationResponse;
import com.okta.commons.lang.Strings;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationStateHandler extends AuthenticationStateHandlerAdapter {

    @Override
    public void handleUnknown(AuthenticationResponse unknownResponse) {
        // redirect to "/error"
    }

    @Override
    public void handleSuccess(AuthenticationResponse successResponse) {

        // a user is ONLY considered authenticated if a sessionToken exists
        if (Strings.hasLength(successResponse.getSessionToken())) {
            String relayState = successResponse.getRelayState();
            String dest = relayState != null ? relayState : "/";
            // redirect to dest
        }
        // other state transition successful
    }

    @Override
    public void handlePasswordExpired(AuthenticationResponse passwordExpired) {
        // redirect to "/login/change-password"
    }

}