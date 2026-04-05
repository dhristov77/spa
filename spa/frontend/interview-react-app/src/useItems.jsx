import { useState } from "react";
import { fetchItems, createItem } from "./api";

export function useItems(ensureValidToken) {
  const [rows, setRows] = useState([]);
  const [error, setError] = useState("");

  const loadItems = async () => {
    try {
      const token = await ensureValidToken();
      const data = await fetchItems(token);
      setRows(data);
      setError("");
    } catch (err) {
      setError(err.message);
    }
  };

  const addItem = async (payload) => {
    try {
      const token = await ensureValidToken();
      await createItem(payload, token);
      await loadItems();
    } catch (err) {
      setError(err.message);
    }
  };

  return { rows, error, loadItems, addItem, setError };
}