import React from 'react';
import { connect } from 'react-redux';
import { createDividend } from '../actions';
import DividendForm from './DividendForm';

class DividendCreate extends React.Component {
  onSubmit = formValues => {
    this.props.createDividend(formValues);
  };

  render() {
    return (
      <DividendForm onSubmit={this.onSubmit} title="Create Dividend" />
    );
  }
}

export default connect(
  null,
  { createDividend }
)(DividendCreate);
