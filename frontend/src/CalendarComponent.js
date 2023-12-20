// import React, { useState } from 'react';
// import DatePicker from 'react-datepicker';
// import 'react-datepicker/dist/react-datepicker.css';
// import {sendSelectedDate} from "./APIServiceGraph"; // Import your API service
//
// const CalendarComponent = () => {
//     const [selectedDate, setSelectedDate] = useState(null);
//
//     const handleDateChange = async (date) => {
//         setSelectedDate(date);
//
//         console.log(date);
//         try {
//             // Make sure the date is in the format your backend expects
//             const formattedDate = date.toISOString().split('T')[0]; // Format as YYYY-MM-DD
//
//             console.log(formattedDate);
//             // Send the selected date to the backend using your API service
//             await sendSelectedDate(formattedDate);
//
//             console.log('Selected Date sent to backend:', formattedDate);
//         } catch (error) {
//             console.error('Error sending selected date:', error);
//         }
//     };
//
//     return (
//         <div>
//             <h3>Select Date:</h3>
//             <DatePicker
//                 selected={selectedDate}
//                 onChange={handleDateChange}
//                 dateFormat="yyyy-MM-dd"
//             />
//         </div>
//     );
// };
//
// export default CalendarComponent;
