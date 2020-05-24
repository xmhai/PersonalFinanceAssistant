import React from 'react';

export const HtmlInput = ({ input, label, meta, type }) => {
    return (
        <>
            <div className="pfa-label">
                <label>{label}</label>
            </div>
            <div className="pfa-input">
                <input {...input} type={type} />
                {renderError(meta)}
            </div>
        </>
    );
};

export const HtmlSelect = ({ input, label, meta, data }) => {
    if (data === undefined || data.length === 0) {
        return (
            <>
                <label>{label}</label>
                <select></select>
            </>
        );
    }

    return (
        <>
            <div className="pfa-label">
                <label>{label}</label>
            </div>
            <div className="pfa-input">
                <select {...input}>
                    <option value=''></option>
                    {data.map((elem, index) => <option key={elem.id} value={elem.id}>{elem.code}</option>)}
                </select>
                {renderError(meta)}
            </div>
        </>
    );
};

const renderError = ({ error, touched }) => {
    if (touched && error) {
        return (
            <span className="error">{error}</span>
        );
    }
}
