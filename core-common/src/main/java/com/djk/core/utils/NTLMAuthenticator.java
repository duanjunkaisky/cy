package com.djk.core.utils;

import jcifs.ntlmssp.NtlmFlags;
import jcifs.ntlmssp.Type1Message;
import jcifs.ntlmssp.Type2Message;
import jcifs.ntlmssp.Type3Message;
import jcifs.util.Base64;
import lombok.NonNull;
import okhttp3.*;

import java.io.IOException;
import java.util.List;

public class NTLMAuthenticator implements Authenticator {

    private static final int TYPE_1_FLAGS =
            NtlmFlags.NTLMSSP_NEGOTIATE_56 |
                    NtlmFlags.NTLMSSP_NEGOTIATE_128 |
                    NtlmFlags.NTLMSSP_NEGOTIATE_NTLM2 |
                    NtlmFlags.NTLMSSP_NEGOTIATE_ALWAYS_SIGN |
                    NtlmFlags.NTLMSSP_REQUEST_TARGET;

    private final String mLogin;
    private final String mPassword;
    private final String mDomain;
    private final String mWorkstation;

    public NTLMAuthenticator(@NonNull String login, @NonNull String password) {
        this(login, password, "", "");
    }

    public NTLMAuthenticator(@NonNull String login, @NonNull String password, @NonNull String domain, @NonNull String workstation) {
        mLogin = login;
        mPassword = password;
        mDomain = domain;
        mWorkstation = workstation;
    }

    @Override
    public Request authenticate(Route route, Response response) {

        List<String> authHeaders = response.headers("WWW-Authenticate");
        boolean negociate = false;
        boolean ntlm = false;
        String ntlmValue = null;
        for (String authHeader : authHeaders) {
            if (authHeader.equalsIgnoreCase("Negotiate")) {
                negociate = true;
            }
            if (authHeader.equalsIgnoreCase("NTLM")) {
                ntlm = true;
            }
            if (authHeader.startsWith("NTLM ")) {
                ntlmValue = authHeader.substring(5);
            }
        }

        if (negociate && ntlm) {
            String type1Msg = generateType1Msg(mDomain, mWorkstation);
            String header = "NTLM " + type1Msg;
            return response.request().newBuilder().header("Authorization", header).build();
        } else if (ntlmValue != null) {
            String type3Msg = generateType3Msg(mLogin, mPassword, mDomain, mWorkstation, ntlmValue);
            String ntlmHeader = "NTLM " + type3Msg;
            return response.request().newBuilder().header("Authorization", ntlmHeader).build();
        }

        if (responseCount(response) <= 3) {
            String credential = Credentials.basic(mLogin, mPassword);
            return response.request().newBuilder().header("Authorization", credential).build();
        }

        return null;
    }

    private String generateType1Msg(@NonNull String domain, @NonNull String workstation) {
        final Type1Message type1Message = new Type1Message(TYPE_1_FLAGS, domain, workstation);
        byte[] source = type1Message.toByteArray();
        return Base64.encode(source);
    }

    private String generateType3Msg(final String login, final String password, final String domain, final String workstation, final String challenge) {
        Type2Message type2Message;
        try {
            byte[] decoded = Base64.decode(challenge);
            type2Message = new Type2Message(decoded);
        } catch (final IOException exception) {
            exception.printStackTrace();
            return null;
        }
        final int type2Flags = type2Message.getFlags();
        final int type3Flags = type2Flags
                & (~(NtlmFlags.NTLMSSP_TARGET_TYPE_DOMAIN | NtlmFlags.NTLMSSP_TARGET_TYPE_SERVER));
        final Type3Message type3Message = new Type3Message(type2Message, password, domain,
                login, workstation, type3Flags);
        return Base64.encode(type3Message.toByteArray());
    }

    private int responseCount(Response response) {
        int result = 1;
        while ((response = response.priorResponse()) != null) {
            result++;
        }
        return result;
    }

}