import React from 'react';

export const HtmlInput = ({ input, label, colspan, meta, type, autoComplete }) => {
    return (
        <>
            <div className="pfa-label">
                <label>{label}</label>
            </div>
            <div className={`pfa-input colspan-${colspan}`}>
                <input {...input} type={type} autoComplete={autoComplete} />
                {renderError(meta)}
            </div>
        </>
    );
};

export const HtmlSelect = ({ input, label, meta, data, display }) => {
    if (data === undefined || data.length === 0) {
        return (
            <>
                <label>{label}</label>
                <select></select>
            </>
        );
    }

    const displayCol = display ? display : "code"; // default to display "code" column
    return (
        <>
            <div className="pfa-label">
                <label>{label}</label>
            </div>
            <div className="pfa-input">
                <select {...input}>
                    <option value=''></option>
                    {data.map((elem, index) => <option key={elem.id} value={elem.id}>{elem[displayCol]}</option>)}
                </select>
                {renderError(meta)}
            </div>
        </>
    );
};

const renderError = ({ error, touched }) => {
    if (touched && error) {
        return (
            <span className="pfa-input-error">{error}</span>
        );
    }
}
