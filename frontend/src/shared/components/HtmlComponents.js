import React from 'react';

export const HtmlInput = ({ input, type, label, meta }) => {
    const className = `field ${meta.error && meta.touched ? 'error' : ''}`;
    return (
        <div className={className}>
            <label>{label}</label>
            <input {...input} type={type} />
            {renderError(meta)}
        </div>
    );
};

const renderError = ({ error, touched }) => {
    if (touched && error) {
        return (
            <div className="ui error message">
                <div className="header">{error}</div>
            </div>
        );
    }
}

export const HtmlSelect = ({ input, label, data }) => {
    if (data === undefined || data.length === 0) {
        return (
            <div className="field">
                <label>{label}</label>
                <select></select>
            </div >
        );
    }

    return (
        <div className="field">
            <label>{label}</label>
            <select {...input}>
                {data.map((elem, index) => <option key={elem.id} value={elem.id}>{elem.code}</option>)}
            </select>
        </div >
    );
};
