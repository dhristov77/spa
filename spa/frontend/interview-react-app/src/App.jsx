import React, { useState, useEffect } from "react";
import "./App.css";
import { useAuth } from "./useAuth";
import { useItems } from "./useItems";

function App() {
  const [number, setNumber] = useState("");
  const [option, setOption] = useState("TYPE1");
  const [text, setText] = useState("");

  const { isAuthenticated, ensureValidToken } = useAuth();
  const { rows, error, loadItems, addItem, setError } =
    useItems(ensureValidToken);

  const handleSubmit = async () => {
    if (!number || !option || !text) {
      setError("All fields required");
      return;
    }

    await addItem({
      number,
      type: option,
      text,
    });

    setNumber("");
    setOption("TYPE1");
    setText("");
  };

  useEffect(() => {
    loadItems();
  }, []);

  return (
    <div className="container">
      {isAuthenticated ? (
        <>
          <div className="row">
            <input
              className="input"
              value={number}
              onChange={(e) => {
                if (/^\d*$/.test(e.target.value)) {
                  setNumber(e.target.value);
                }
              }}
              placeholder="Numbers only"
            />

            <select
              className="input"
              value={option}
              onChange={(e) => setOption(e.target.value)}
            >
              <option value="TYPE1">TYPE 1</option>
              <option value="TYPE2">TYPE 2</option>
              <option value="TYPE3">TYPE 3</option>
            </select>

            <input
              className="input"
              value={text}
              onChange={(e) => setText(e.target.value)}
              placeholder="Enter text"
            />

            <button className="button" onClick={handleSubmit}>
              Add
            </button>
          </div>

          {error && <div className="error-message">{error}</div>}

          <table className="table">
            <thead>
              <tr>
                <th className="th">Number</th>
                <th className="th">Type</th>
                <th className="th">Text</th>
              </tr>
            </thead>
            <tbody>
              {rows.map((row) => (
                <tr key={row.id} className="tr">
                  <td className="td">{row.number}</td>
                  <td className="td">{row.type}</td>
                  <td className="td">{row.text}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </>
      ) : (
        <div className="error-message">Loading...</div>
      )}
    </div>
  );
}

export default App
