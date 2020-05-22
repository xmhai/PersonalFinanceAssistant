import React from 'react';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';

import { fetchPortfolios } from '../actions';

class PortfolioView extends React.Component {
  componentDidMount() {
    const { id } = this.props.match.params;

    this.props.fetchPortfolios(id);
  }

  render() {
    if (!this.props.portfolio) {
      return <div>Loading...</div>;
    }

    const { instituteName, portfolioNo } = this.props.portfolio;

    return (
      <div>
        <h1>{instituteName}</h1>
        <h5>{portfolioNo}</h5>
        <Link to="/portfolios" className="ui button primary">
          Back to List
        </Link>
      </div>
    );
  }
}

const mapStateToProps = (state, ownProps) => {
  return { portfolio: state.portfolios[ownProps.match.params.id] };
};

export default connect(
  mapStateToProps,
  { fetchPortfolios }
)(PortfolioView);
