import React from 'react';

export const ColorNumber = ({ value }) => {
    if (value || value === 0) {
        return (
            <div style={{ color: value >= 0 ? "green" : "red", textAlign: "right" } }>{value.toLocaleString('en-US', { minimumFractionDigits: 2 })} </div>
        );
    }

    // undefine
    return <div />
};

export const Number = ({ value }) => {
    if (value || value === 0) {
        return (
            <div>{value.toLocaleString('en-US', { minimumFractionDigits: 2 })} </div>
        );
    }

    return <div />
};
