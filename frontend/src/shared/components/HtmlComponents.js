import React from 'react';

export const HtmlInput = ({ props, type, label, meta }) => {
    const className = `field ${meta.error && meta.touched ? 'error' : ''}`;
    return (
        <div className={className}>
            <label>{label}</label>
            <input {...props} type={type} />
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

export const HtmlSelect = ({ props, label, data }) => {
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
            <select {...props}>
                {data.map((elem, index) => <option key={elem.id} value={elem.id}>{elem.code}</option>)}
            </select>
        </div >
    );
};
