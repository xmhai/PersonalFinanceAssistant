import React from 'react';

export const ColorNumber = ({ value }) => {
    return (
        <div style={{ color: value > 0 ? "green" : "red", textAlign: "right" } }>{value.toLocaleString('en-US', { minimumFractionDigits: 2 })} </div>
    );
};

export const Number = ({ value }) => {
    return (
        <div>{value.toLocaleString('en-US', { minimumFractionDigits: 2 })} </div>
    );
};
