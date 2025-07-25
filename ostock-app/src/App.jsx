import { BrowserRouter, Route, Routes } from 'react-router-dom'
import './App.css'
import License from './License'
import Login from './Login'

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<License />} />
        <Route path="/login" element={<Login />} />
        <Route path="/license" element={<License />} />
      </Routes>
    </BrowserRouter>
  )
}

export default App
