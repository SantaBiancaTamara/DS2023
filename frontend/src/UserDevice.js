import React, { useState, useEffect } from 'react';
import Table from 'react-bootstrap/Table';
import axios from 'axios';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';

import {
    ResponsiveContainer,
    BarChart,
    Bar,
    XAxis,
    YAxis,
    Tooltip,
    Legend,
} from 'recharts';


const UserDevice = ({ userId }) => {
    const [devices, setDevices] = useState([]);
    const [selectedDate, setSelectedDate] = useState(null);
    const [energyData, setEnergyData] = useState([]);
    const [chartData, setChartData] = useState([]);
    const chartColors = {
        chartBackground: '#ffffff',
        barFill: '#3366ff', // Example colors, use your preferred colors here
    };


    const handleDateChange = async (date) => {
        setSelectedDate(date);

        console.log(date);
        try {
            // Make sure the date is in the format your backend expects
            const formattedDate = date.toISOString().split('T')[0]; // Format as YYYY-MM-DD

            console.log(formattedDate);
            // Send the selected date to the backend using your API service
            await sendSelectedDate(formattedDate);

            console.log('Selected Date sent to backend:', formattedDate);
        } catch (error) {
            console.error('Error sending selected date:', error);
        }
    };

    const sendSelectedDate = async (selectedDate, userId) => {
        try {
            const response = await fetch(`http://localhost:8082/measurement/graphData/${selectedDate}/${sessionStorage.getItem("userId")}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    // Add any necessary headers (e.g., authorization token)
                }
            });

            if (response.ok) {
                const data = await response.json();
                console.log('Data from backend:', data);
                setEnergyData(data);
                return data; // Return data if needed
            } else {
                throw new Error('Failed to send selected date to the backend');
            }
        } catch (error) {
            console.error('Error:', error.message);
            throw error;
        }
    };

    useEffect(() => {
        console.log(userId);
        console.log(sessionStorage.getItem("userId"));
        axios.get(`http://localhost:8081/devices/seeAllUserDevices/${sessionStorage.getItem("userId")}`)
            .then(res => {setDevices(res.data)})
            .catch(err => console.log(err))
    }, []);

    useEffect(() => {
        if (energyData && energyData.length > 0) {
            const formattedData = energyData.map((dataPoint) => ({
                hour: dataPoint.hour,
                value: dataPoint.consumption,
            }));

            setChartData(formattedData);
        }
    }, [energyData]);



    return (
        <div>
        <Table striped bordered hover>
            <thead>
            <tr>
                <th>No. devices</th>
                <th>ID</th>
                <th>Description</th>
                <th>Address</th>
                <th>Energy Time</th>
                {/* Add more headers for additional fields if needed */}
            </tr>
            </thead>
            <tbody>
            {devices.map((device, index) => (
                <tr key={device.id}>
                    <td>{index }</td>
                    <td>{device.id}</td>
                    <td>{device.description}</td>
                    <td>{device.address}</td>
                    <td>{device.energyTime}</td>
                    {/* Add more columns for additional fields if needed */}
                </tr>
            ))}
            </tbody>
        </Table>

            <div>
                <h3>Select Date:</h3>
                <DatePicker
                    selected={selectedDate}
                    onChange={handleDateChange}
                    dateFormat="yyyy-MM-dd"
                />
            </div>

            {chartData && chartData.length > 0 && (
                <ResponsiveContainer width="100%" height={400}>
                    <BarChart
                        data={chartData}
                        margin={{ top: 20, right: 30, left: 20, bottom: 5 }}
                    >
                        <XAxis dataKey="hour" />
                        <YAxis />
                        <Tooltip />
                        <Legend />
                        <Bar dataKey="value" fill={chartColors.barFill} />
                    </BarChart>
                </ResponsiveContainer>
            )}
            {/*<CalendarComponent /> /!* Pass the handler to CalendarComponent *!/*/}
        </div>

    );
};


export default UserDevice;
