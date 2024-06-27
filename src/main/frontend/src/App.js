import React, { useState } from 'react';
import axios from 'axios';
import './App.css';
import ImageGrid from "./components/ImageGrid"; // Create this file for ChatGPT-like colors

const App = () => {
  const [query, setQuery] = useState('');
  const [results, setResults] = useState([]);

  const handleSearch = async () => {
    try {
      const response = await axios.get(`http://genesis:8080/PictSeek/v1/solr/query?q=${query}`);
      setResults(response.data.response.docs);
    } catch (error) {
      console.error('Error fetching data:', error);
    }
  };

  return (
      <div className="App">
          <header className="App-header">
              <h1>PictSeek Archive</h1>
              <input
                  type="text"
                  value={query}
                  onChange={(e) => setQuery(e.target.value)}
                  placeholder="Enter search term..."
              />
              <button onClick={handleSearch}>Search</button>
          </header>

          <div>
              <h1>Responsive Image Grid</h1>
              <ImageGrid images={results}/>
          </div>

      </div>
  );
};

export default App;
