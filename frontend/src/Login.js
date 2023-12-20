import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const Login = () => {
    const navigate = useNavigate();

    const handleSubmit = async (event) => {
        event.preventDefault();
        const formData = {
            email: event.target.email.value,
            password: event.target.password.value
        };

        try {
            const response = await fetch("http://localhost:8080/api/v1/auth/login", {
                credentials: 'include',
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(formData)
            });

            if (response.ok) {
                const data = await response.json();
                console.log(data.userId);
                sessionStorage.setItem("userId", data.userId);
                console.log(sessionStorage.getItem("userId"));
                // Redirect to UserDevice page upon successful login
                navigate('/userDevices');
            } else {
                console.error('Login failed');
            }
        } catch (error) {
            console.error('Error occurred:', error);
        }
    };


    return (
        <div className="Login-form-container">
            <form className="Login-form" onSubmit={handleSubmit}>
                <div className="Login-form-content">
                    <h3 className="Login-form-title">Sign In</h3>
                    <div className="form-group mt-3">
                        <label>Email address</label>
                        <input
                            type="email"
                            name="email" // Added name attribute
                            className="form-control mt-1"
                            placeholder="Enter email"
                        />
                    </div>
                    <div className="form-group mt-3">
                        <label>Password</label>
                        <input
                            type="password"
                            name="password" // Added name attribute
                            className="form-control mt-1"
                            placeholder="Enter password"
                        />
                    </div>
                    <div className="d-grid gap-2 mt-3">
                        <button type="submit" className="btn btn-primary">
                            Submit
                        </button>
                    </div>
                </div>
            </form>
        </div>
    )
};
export default Login;