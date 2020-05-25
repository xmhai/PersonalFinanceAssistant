import React from 'react';
import { connect } from 'react-redux';
import { createStock } from '../actions';
import StockForm from './StockForm';

class StockCreate extends React.Component {
  onSubmit = formValues => {
    this.props.createStock(formValues);
  };

  render() {
    return (
      <StockForm onSubmit={this.onSubmit} title="Create Stock" />
    );
  }
}

export default connect(
  null,
  { createStock }
)(StockCreate);
