import React from 'react';

export const ColorNumber = ({ value }) => {
    if (value || value === 0) {
        return (
            <div style={{ color: value >= 0 ? "green" : "red", textAlign: "right" }}>
                {value.toLocaleString(undefined, { minimumFractionDigits: 2, maximumFractionDigits: 2 })}
            </div>
        );
    }

    // undefine
    return <div />
};

export const Number = ({ value }) => {
    if (value || value === 0) {
        return (
            <div style={{ textAlign: "right" }}>
                {value.toLocaleString(undefined, { minimumFractionDigits: 2, maximumFractionDigits: 2 })}
            </div>
        );
    }

    return <div />
};
