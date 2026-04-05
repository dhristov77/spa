const API_URL = "http://localhost:8080/items";
const LOGIN_URL = "http://localhost:8080/auth/login";

export const getToken = () => localStorage.getItem("jwtToken");

export const setToken = (token) => {
  localStorage.setItem("jwtToken", token);
};

export const decodeJWT = (token) => {
  const base64Url = token.split('.')[1];
  const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
  const jsonPayload = decodeURIComponent(
    atob(base64)
      .split('')
      .map(c => '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2))
      .join('')
  );
  return JSON.parse(jsonPayload);
};

export const isTokenExpired = (token) => {
  const decoded = decodeJWT(token);
  const currentTime = Math.floor(Date.now() / 1000);
  return decoded.exp < currentTime;
};

export const login = async (username, password) => {
  const response = await fetch(LOGIN_URL, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ username, password }),
  });

  if (!response.ok) throw new Error("Invalid login");

  const data = await response.json();
  setToken(data.token);
  return data.token;
};

export const fetchItems = async (token) => {
  const response = await fetch(API_URL, {
    headers: { Authorization: `Bearer ${token}` },
  });

  if (!response.ok) throw new Error("Failed to fetch");
  return response.json();
};

export const createItem = async (payload, token) => {
  const response = await fetch(API_URL, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
    },
    body: JSON.stringify(payload),
  });

  if (!response.ok) throw new Error("Failed to save");
};