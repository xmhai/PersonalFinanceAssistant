import React from 'react';
import { connect } from 'react-redux';
import { createAccount } from '../actions';
import AccountForm from './AccountForm';

class AccountCreate extends React.Component {
  onSubmit = formValues => {
    this.props.createAccount(formValues);
  };

  render() {
    return (
      <AccountForm onSubmit={this.onSubmit} title="Create Account" />
    );
  }
}

export default connect(
  null,
  { createAccount }
)(AccountCreate);
