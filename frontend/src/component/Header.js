import React from 'react'

export const Header =  () => {
        const headerStyle = {
            width: '100%',
            padding: '2%',
            backgroundColor: "midnightblue",
            color: 'white',
            textAlign: 'center'
        }

        return (
            <div style={headerStyle}>
                <h2>UCLH Admmision Report</h2>
            </div>
        )
}
