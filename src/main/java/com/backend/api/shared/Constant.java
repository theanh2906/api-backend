package com.backend.api.shared;

public class Constant {
    public static final String ADMIN_ID = "629ce7bb-2713-43be-a9bf-1538ff77218a";
    public static final String[] CROSS_ORIGIN_ALLOW_LIST = new String[]{
            "http://localhost:8080", "http://localhost:4200",
            "https://benna.vercel.app", "https://theanh2906.ddns.net", "http://localhost:8888"
    };

    public static class HeaderAttribute {
        public static final String AUTHORIZATION = "Authorization";
        public static final String CONTENT_TYPE = "Content-Type";
        public static final String ACCEPT = "Accept";
        public static final String USER_AGENT = "User-Agent";
        public static final String ACCEPT_LANGUAGE = "Accept-Language";
        public static final String ACCEPT_ENCODING = "Accept-Encoding";
        public static final String ACCEPT_CHARSET = "Accept-Charset";
        public static final String ACCEPT_DATETIME = "Accept-Datetime";
        public static final String ACCEPT_RANGES = "Accept-Ranges";
        public static final String ACCEPT_PATCH = "Accept-Patch";
        public static final String ACCEPT_POST = "Accept-Post";
        public static final String ACCEPT_PROPOSAL = "Accept-Proposal";
        public static final String ACCEPT_QUERY = "Accept-Query";
        public static final String ACCEPT_RDF = "Accept-RDF";
        public static final String ACCEPT_RSS = "Accept-RSS";
        public static final String ACCEPT_SCHEME = "Accept-Scheme";
        public static final String ACCEPT_SESSION = "Accept-Session";
        public static final String ACCEPT_SVG = "Accept-SVG";
        public static final String ACCEPT_TEXT = "Accept-Text";
        public static final String ACCEPT_XML = "Accept-XML";
        public static final String ACCEPT_XHTML = "Accept-XHTML";
        public static final String ACCEPT_XSL = "Accept-XSL";
        public static final String ACCEPT_X_JAVASCRIPT = "Accept-X-Javascript";
        public static final String ACCEPT_X_LINK = "Accept-X-Link";
        public static final String ACCEPT_X_STYLESHEET = "Accept-X-Stylesheet";
        public static final String ACCEPT_X_SVG = "Accept-X-SVG";
        public static final String ACCEPT_X_HTML = "Accept-X-HTML";
        public static final String ACCEPT_X_HTLM = "Accept-X-HTLM";
        public static final String SESSION_ID = "session_id";
    }

    public static class Role {
        public static final long USER = 3;
        public static final long ADMIN = 1;
    }

    public static class Azure {
        public static final String CLIENT_SECRET = "7ry8Q~jqQC1FL1R6V5.wh.rx7kZRG6dv-G-hOdhx";
        public static final String TENANT_ID = "90d076c5-6610-4955-bccd-4f99ae488ef0";
        //        public static final String CLIENT_ID = "48447683-68aa-45c3-beac-c613aee85234";
        public static final String CLIENT_ID = "48447683-68aa-45c3-beac-c613aee85234";
        public static final String[] SCOPES = new String[] {
                ".default",
        };
        public static final String REDIRECT_URI = "http://localhost:8082/api/azure/callback/";
        public static final String[] ALLOWED_HOSTS = new String[]{"*"};
        public static final String QUEUE_NAME = "benna-queue";
        public static final String QUEUE_NAME_ENDPOINT = "https://benna.queue.core.windows.net/benna-queue";
        public static final String STORAGE_CONNECTION_STRING = "DefaultEndpointsProtocol=https;AccountName=benna;AccountKey=GNjwKgTb919QBC8oN84mOgSUsUXCQwR61KhCnpMrDom7aAg4fpzqkQakeJuGDN9nr0XU7zRS5iYA+ASthMRkMA==;EndpointSuffix=core.windows.net";
        public static final String VAULT_URL = "https://benna-key-vault.vault.azure.net/";
    }
}
