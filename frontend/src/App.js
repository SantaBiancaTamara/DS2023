import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import './App.css';
import "bootstrap/dist/css/bootstrap.min.css";
import Login from "./Login";
import UserDevice from './UserDevice';
import {useState} from "react";

function App() {
    const [loggedIn, setLoggedIn] = useState(false);
    const [userId, setUserId] = useState(null);

    const handleLogin = (userData) => {
        // Assuming the login response contains 'userId' and 'token'
        setUserId(userData.userId); // Store user ID
        // Handle token storage if needed

        setLoggedIn(true); // Set login state to true after successful login
    };

    return (
        <BrowserRouter>
            <Routes>
                <Route
                    path="/"
                    element={loggedIn ? <Navigate to="/userDevices" /> : <Login handleLogin={handleLogin} />}
                />
                <Route
                    path="/userDevices"
                    element={<UserDevice userId={sessionStorage.getItem("userId")} />} // Pass userId to UserDevice component
                />
            </Routes>
        </BrowserRouter>
    );
}

export default App;
