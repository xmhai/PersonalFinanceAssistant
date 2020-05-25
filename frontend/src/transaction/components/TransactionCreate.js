import React from 'react';
import { connect } from 'react-redux';
import { createTransaction } from '../actions';
import TransactionForm from './TransactionForm';

class TransactionCreate extends React.Component {
  onSubmit = formValues => {
    this.props.createTransaction(formValues);
  };

  render() {
    return (
      <TransactionForm onSubmit={this.onSubmit} title="Create Transaction" />
    );
  }
}

export default connect(
  null,
  { createTransaction }
)(TransactionCreate);
