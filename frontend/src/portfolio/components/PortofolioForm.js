import React from 'react';
import { connect } from 'react-redux';
import { Field, reduxForm } from 'redux-form';
import { DatePicker } from 'antd';
import moment from 'moment';
import 'antd/dist/antd.css';

import { fetchCategories } from '../../common/actions';
import { fetchCurrencies } from '../../common/actions';

class PortfolioForm extends React.Component {
  componentDidMount() {
    this.props.fetchCategories();
    this.props.fetchCurrencies();
  }

  renderError({ error, touched }) {
    if (touched && error) {
      return (
        <div className="ui error message">
          <div className="header">{error}</div>
        </div>
      );
    }
  }

  renderInput = ({ input, type, label, meta }) => {
    const className = `field ${meta.error && meta.touched ? 'error' : ''}`;
    return (
      <div className={className}>
        <label>{label}</label>
        <input {...input} type={type} />
        {this.renderError(meta)}
      </div>
    );
  };

  renderDate = ({ input, label, meta }) => {
    const className = `field ${meta.error && meta.touched ? 'error' : ''}`;
    console.log(input)
    return (
      <div className={className}>
        <label>{label}</label>
        <DatePicker defaultValue={moment(input.value, 'YYYY-MM-DD')} format='DD/MM/YYYY' />
        {this.renderError(meta)}
      </div>
    );
  };

  renderDropdown = ({ input, label, data }) => {
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
          { data.map( (elem, index) => <option key={elem.id} value={elem.id}>{elem.code}</option>) }
        </select>
      </div >
    );
  };

  onSubmit = formValues => {
    this.props.onSubmit(formValues);
  };

  render() {
    return (
      <form onSubmit={this.props.handleSubmit(this.onSubmit)} className="ui form error" >
        <Field name="instituteName" component={this.renderInput} label="Institute Name" />
        <Field name="portfolioNo" component={this.renderInput} label="Portfolio No.:" />
        <Field name="portfolioHolder" component={this.renderInput} label="Portfolio Holder:" />
        <Field name="categoryId" component={this.renderDropdown} data={this.props.categories} label="Asset Category:" />
        <Field name="currencyId" component={this.renderDropdown} data={this.props.currencies} label="Currency:" />
        <Field name="amount" component={this.renderInput} label="Amount:" />
        <Field name="maturityDate" component={this.renderDate} type="date" label="Maturity Date:" />
        <button className="ui button primary">Submit</button>
      </form>
    );
  }
}

const validate = formValues => {
  const errors = {};

  if (!formValues.instituteName) {
    errors.instituteName = 'You must enter a title';
  }

  if (!formValues.portfolioNo) {
    errors.portfolioNo = 'You must enter a description';
  }

  return errors;
};

const mapStateToProps = state => {
  return {
    categories: Object.values(state.config.categories),
    currencies: Object.values(state.config.currencies)
  };
};

PortfolioForm = connect(mapStateToProps, { fetchCategories, fetchCurrencies } )(PortfolioForm);

export default reduxForm({
  form: 'portfolioForm',
  validate
})(PortfolioForm);
