// 서비스 URL 환경 변수
const SECURITY_SERVER_URL = process.env.SECURITY_SERVER_URL;
const ASSET_SERVICE_URL = process.env.ASSET_SERVICE_URL;

const SERVICES = {
    MEMBER: process.env.MEMBER_SERVICE_URL,
    ORDER: process.env.ORDER_SERVICE_URL,
    PAYMENT: process.env.PAYMENT_SERVICE_URL,
};

const ROUTING_MAP = [
    { prefix: "/api/auth", url: SECURITY_SERVER_URL, internalPrefix: "/auth" },
    { prefix: "/api/asset", url: ASSET_SERVICE_URL, internalPrefix: "/asset" },
    { prefix: "/api/member", url: SERVICES.MEMBER, internalPrefix: "/member" },
    { prefix: "/api/orders", url: SERVICES.ORDER, internalPrefix: "/orders" },
    { prefix: "/api/payment", url: SERVICES.PAYMENT, internalPrefix: "/payment" },
];

const PROTECTED_PATH_RE = /^\/api\/(member|orders|payment)(\/|$)/i;
const PUBLIC_AUTH_PATH_RE = /^\/api\/auth\/(login|logout|refresh|v1\/login)$/i;
const FRONTEND_URL = process.env.FRONTEND_URL;
const ALLOWED_ORIGINS = [FRONTEND_URL, ];

export {
    SECURITY_SERVER_URL,
    ASSET_SERVICE_URL,
    ROUTING_MAP,
    PROTECTED_PATH_RE,
    PUBLIC_AUTH_PATH_RE,
    FRONTEND_URL,
    ALLOWED_ORIGINS,
};