import React from 'react';
import { connect } from 'react-redux';
import { createProfit } from '../actions';
import ProfitForm from './ProfitForm';

class ProfitCreate extends React.Component {
  onSubmit = formValues => {
    this.props.createProfit(formValues);
  };

  render() {
    return (
      <ProfitForm onSubmit={this.onSubmit} title="Create Profit" />
    );
  }
}

export default connect(
  null,
  { createProfit }
)(ProfitCreate);
