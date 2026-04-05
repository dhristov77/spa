import { useState } from "react";
import { login, getToken, isTokenExpired } from "./api";

const USERNAME = "admin";
const PASSWORD = "admin";

export function useAuth() {
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  const ensureValidToken = async () => {
    let token = getToken();

    if (!token || isTokenExpired(token)) {
      token = await login(USERNAME, PASSWORD);
    }

    setIsAuthenticated(true);
    return token;
  };

  return { isAuthenticated, ensureValidToken };
}
