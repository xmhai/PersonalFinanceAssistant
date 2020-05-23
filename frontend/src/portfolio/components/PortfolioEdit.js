import React from 'react';
import { connect } from 'react-redux';
import { fetchPortfolio, editPortfolio } from '../actions';
import PortfolioForm from './PortfolioForm';

class PortfolioEdit extends React.Component {
  componentDidMount() {
    this.props.fetchPortfolio(this.props.match.params.id);
  }

  onSubmit = formValues => {
    this.props.editPortfolio(this.props.match.params.id, formValues);
  };

  render() {
    if (!this.props.portfolio) {
      return <div>Loading...</div>;
    }

    return (
      <div>
        <h3>Edit Portfolio</h3>
        <PortfolioForm
          initialValues={this.props.portfolio}
          onSubmit={this.onSubmit}
        />
      </div>
    );
  }
}

const mapStateToProps = (state, ownProps) => {
  return { portfolio: state.portfolios[ownProps.match.params.id] };
};

export default connect(
  mapStateToProps,
  { fetchPortfolio, editPortfolio }
)(PortfolioEdit);
