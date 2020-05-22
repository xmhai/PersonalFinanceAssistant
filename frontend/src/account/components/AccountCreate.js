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
      <div>
        <h3>Create a Account</h3>
        <AccountForm onSubmit={this.onSubmit} />
      </div>
    );
  }
}

export default connect(
  null,
  { createAccount }
)(AccountCreate);
