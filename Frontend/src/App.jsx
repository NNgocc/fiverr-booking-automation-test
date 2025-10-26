import { useState, useEffect } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'

function App() {
  const [count, setCount] = useState(0)
  const [weatherData, setWeatherData] = useState([])
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState(null)

  const fetchWeatherData = async () => {
    setLoading(true)
    setError(null)
    try {
      const response = await fetch('http://localhost:5000/api/WeatherForecast')
      if (!response.ok) {
        throw new Error('Failed to fetch data')
      }
      const data = await response.json()
      setWeatherData(data)
    } catch (err) {
      setError(err.message)
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    fetchWeatherData()
  }, [])

  return (
    <>
      <div>
        <a href="https://vite.dev" target="_blank">
          <img src={viteLogo} className="logo" alt="Vite logo" />
        </a>
        <a href="https://react.dev" target="_blank">
          <img src={reactLogo} className="logo react" alt="React logo" />
        </a>
      </div>
      <h1>React + .NET Core 8 Integration</h1>

      <div className="card">
        <button onClick={() => setCount((count) => count + 1)}>
          count is {count}
        </button>
        <p>
          Edit <code>src/App.jsx</code> and save to test HMR
        </p>
      </div>

      <div className="card">
        <h2>Weather Forecast from API</h2>
        <button onClick={fetchWeatherData} disabled={loading}>
          {loading ? 'Loading...' : 'Refresh Weather Data'}
        </button>

        {error && <p style={{ color: 'red' }}>Error: {error}</p>}

        {weatherData.length > 0 && (
          <table style={{ margin: '20px auto', borderCollapse: 'collapse' }}>
            <thead>
              <tr>
                <th style={{ border: '1px solid #ddd', padding: '8px' }}>Date</th>
                <th style={{ border: '1px solid #ddd', padding: '8px' }}>Temp (C)</th>
                <th style={{ border: '1px solid #ddd', padding: '8px' }}>Temp (F)</th>
                <th style={{ border: '1px solid #ddd', padding: '8px' }}>Summary</th>
              </tr>
            </thead>
            <tbody>
              {weatherData.map((weather, index) => (
                <tr key={index}>
                  <td style={{ border: '1px solid #ddd', padding: '8px' }}>{weather.date}</td>
                  <td style={{ border: '1px solid #ddd', padding: '8px' }}>{weather.temperatureC}</td>
                  <td style={{ border: '1px solid #ddd', padding: '8px' }}>{weather.temperatureF}</td>
                  <td style={{ border: '1px solid #ddd', padding: '8px' }}>{weather.summary}</td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>

      <p className="read-the-docs">
        Click on the Vite and React logos to learn more
      </p>
    </>
  )
}

export default App
