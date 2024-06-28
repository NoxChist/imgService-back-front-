import { jwtDecode } from "jwt-decode";

function getToken() {
    return localStorage.getItem('token');
}

function decodeToken(token) {
    if (token) {
        return jwtDecode(token);
    }
    return null;
}
export function getSub() {
    const token = getToken();
    if (token) {
        const decoded = decodeToken(token);
        return decoded.sub;
    }
    return false;
}

export function isAdminOrUser() {
    const token = getToken();
    if (token) {
        const decoded = decodeToken(token);
        const roles = decoded?.authorities || [];
        return roles.includes('ROLE_ADMIN') || roles.includes('ROLE_USER');
    }
    return false;
}

export function isAdmin() {
    const token = getToken();
    if (token) {
        const decoded = decodeToken(token);
        const roles = decoded?.authorities || [];
        return roles.includes('ROLE_ADMIN');
    }
    return false;
}

export function isUser() {
    const token = getToken();
    if (token) {
        const decoded = decodeToken(token);
        const roles = decoded?.authorities || [];
        return roles.includes('ROLE_USER');
    }
    return false;
}