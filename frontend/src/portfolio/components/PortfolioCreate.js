import React from 'react';
import { connect } from 'react-redux';
import { createPortfolio } from '../actions';
import PortfolioForm from './PortfolioForm';

class PortfolioCreate extends React.Component {
  onSubmit = formValues => {
    this.props.createPortfolio(formValues);
  };

  render() {
    return (
      <div>
        <h3>Create a Portfolio</h3>
        <PortfolioForm onSubmit={this.onSubmit} />
      </div>
    );
  }
}

export default connect(
  null,
  { createPortfolio }
)(PortfolioCreate);
