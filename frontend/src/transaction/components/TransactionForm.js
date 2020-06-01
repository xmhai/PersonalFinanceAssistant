import React from 'react';
import { connect } from 'react-redux';
import { Field, reduxForm } from 'redux-form';
import { Link } from 'react-router-dom';
import _ from 'lodash';

import { fetchStocks } from '../../stock/actions';
import { fetchActions } from '../../common/actions';
import { HtmlInput, HtmlSelect } from '../../shared/components/HtmlComponents';

class TransactionForm extends React.Component {
  componentDidMount() {
    if (_.isEmpty(this.props.stocks)) this.props.fetchStocks();
    if (_.isEmpty(this.props.actions)) this.props.fetchActions();
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
          <Field name="stockId" label="Stock:" component={HtmlSelect} data={this.props.stocks} display="name" />
          <Field name="transactionDate" label="Transaction Date:" component={HtmlInput} type="date" />
          <Field name="action" label="Action:" component={HtmlSelect} data={this.props.actions} />
          <Field name="price" label="Price:" component={HtmlInput} autoComplete="off" />
          <Field name="quantity" label="Quantity:" component={HtmlInput} autoComplete="off" />
          <div className="pfa-form-button">
            <button className="ui button primary">Submit</button>
            <Link to="/transactions" className="ui button">
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

  if (!formValues.transactionNo) {
    errors.transactionNo = 'Please enter Transaction Number';
  }

  return errors;
};

const mapStateToProps = state => {
  if (state.stocks && state.config.actions) {
    return {
      stocks: _.orderBy(Object.values(state.stocks), ['name'], ['asc']),
      actions: Object.values(state.config.actions),
    };
  }

  return {};
};

TransactionForm = connect(mapStateToProps, { fetchStocks, fetchActions } )(TransactionForm);

export default reduxForm({
  form: 'transactionForm',
  validate
})(TransactionForm);
