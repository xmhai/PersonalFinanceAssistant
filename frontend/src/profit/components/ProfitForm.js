import React from 'react';
import { connect } from 'react-redux';
import { Field, reduxForm } from 'redux-form';
import { Link } from 'react-router-dom';
import _ from 'lodash';

import { fetchStocks } from '../../stock/actions';
import { HtmlInput, HtmlSelect } from '../../shared/components/HtmlComponents';

class ProfitForm extends React.Component {
  componentDidMount() {
    if (_.isEmpty(this.props.stocks)) this.props.fetchStocks();
  }

  onSubmit = formValues => {
    this.props.onSubmit(formValues);
  };

  render() {
    return (
      <>
        <div className="pfa-form-title">{this.props.title}</div>
        <div className="ui divider"></div>
        <form onSubmit={this.props.handleSubmit(this.onSubmit)} className="ui form error pfa-form" >
          <Field name="stock.id" label="Stock:" component={HtmlSelect} data={this.props.stocks} display="name" />
          <Field name="realized" label="Realized:" component={HtmlInput} />
          <Field name="dividend" label="Dividend:" component={HtmlInput} />
          <div className="pfa-form-button">
            <button className="ui button primary">Submit</button>
            <Link to="/profits" className="ui button">
              Cancel
            </Link>
            </div>
        </form>
      </>
    );
  }
}

const validate = formValues => {
  const errors = {};

  if (!formValues.instituteName) {
    errors.instituteName = 'Please enter Institute Name';
  }

  if (!formValues.profitNo) {
    errors.profitNo = 'Please enter Profit Number';
  }

  return errors;
};

const mapStateToProps = state => {
  return {
    stocks: Object.values(state.stocks),
  };
};

ProfitForm = connect(mapStateToProps, { fetchStocks } )(ProfitForm);

export default reduxForm({
  form: 'profitForm',
  validate
})(ProfitForm);
