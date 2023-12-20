// export const sendSelectedDate = async (selectedDate, userId) => {
//     try {
//         const response = await fetch(`http://localhost:8082/measurement/graphData/${selectedDate}/${sessionStorage.getItem("userId")}`, {
//             method: 'GET',
//             headers: {
//                 'Content-Type': 'application/json',
//                 // Add any necessary headers (e.g., authorization token)
//             }
//         });
//
//         if (response.ok) {
//             const data = await response.json();
//             console.log('Data from backend:', data);
//             return data; // Return data if needed
//         } else {
//             throw new Error('Failed to send selected date to the backend');
//         }
//     } catch (error) {
//         console.error('Error:', error.message);
//         throw error;
//     }
// };
